package br.com.sbk.saml2.idp.execution;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.SignableXMLObject;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.com.sbk.saml2.idp.dto.SAMLAttribute;
import br.com.sbk.saml2.idp.dto.SAMLPrincipal;

public class SAMLBuilder {

	private static final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	@SuppressWarnings({ "unchecked" })
	public static <T> T buildSAMLObject(final Class<T> objectClass, QName qName) {
		return (T) builderFactory.getBuilder(qName).buildObject(qName);
	}

	public static Issuer buildIssuer(String issuingEntityName) {
		Issuer issuer = buildSAMLObject(Issuer.class, Issuer.DEFAULT_ELEMENT_NAME);
		issuer.setValue(issuingEntityName);
		issuer.setFormat(NameIDType.ENTITY);
		return issuer;
	}

	private static Subject buildSubject(String subjectNameId, String subjectNameIdType, String recipient,
			String inResponseTo) {
		NameID nameID = buildSAMLObject(NameID.class, NameID.DEFAULT_ELEMENT_NAME);
		nameID.setValue(subjectNameId);
		nameID.setFormat(subjectNameIdType);

		Subject subject = buildSAMLObject(Subject.class, Subject.DEFAULT_ELEMENT_NAME);
		subject.setNameID(nameID);

		SubjectConfirmation subjectConfirmation = buildSAMLObject(SubjectConfirmation.class,
				SubjectConfirmation.DEFAULT_ELEMENT_NAME);
		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

		SubjectConfirmationData subjectConfirmationData = buildSAMLObject(SubjectConfirmationData.class,
				SubjectConfirmationData.DEFAULT_ELEMENT_NAME);

		subjectConfirmationData.setRecipient(recipient);
		subjectConfirmationData.setInResponseTo(inResponseTo);
		subjectConfirmationData.setNotOnOrAfter(new DateTime().plusMinutes(8 * 60));
		subjectConfirmationData.setAddress(recipient);

		subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);

		subject.getSubjectConfirmations().add(subjectConfirmation);

		return subject;
	}

	public static Status buildStatus(String value) {
		Status status = buildSAMLObject(Status.class, Status.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = buildSAMLObject(StatusCode.class, StatusCode.DEFAULT_ELEMENT_NAME);
		statusCode.setValue(value);
		status.setStatusCode(statusCode);
		return status;
	}

	public static Status buildStatus(String value, String subStatus, String message) {
		Status status = buildStatus(value);

		StatusCode subStatusCode = buildSAMLObject(StatusCode.class, StatusCode.DEFAULT_ELEMENT_NAME);
		subStatusCode.setValue(subStatus);
		status.getStatusCode().setStatusCode(subStatusCode);

		StatusMessage statusMessage = buildSAMLObject(StatusMessage.class, StatusMessage.DEFAULT_ELEMENT_NAME);
		statusMessage.setMessage(message);
		status.setStatusMessage(statusMessage);

		return status;
	}

	public static Assertion buildAssertion(SAMLPrincipal principal, Status status, String entityId) {
		Assertion assertion = buildSAMLObject(Assertion.class, Assertion.DEFAULT_ELEMENT_NAME);

		if (status.getStatusCode().getValue().equals(StatusCode.SUCCESS_URI)) {
			Subject subject = buildSubject(principal.getNameID(), principal.getNameIDType(),
					principal.getAssertionConsumerServiceURL(), principal.getRequestID());
			assertion.setSubject(subject);
		}

		Issuer issuer = buildIssuer(entityId);

		Audience audience = buildSAMLObject(Audience.class, Audience.DEFAULT_ELEMENT_NAME);
		audience.setAudienceURI(principal.getServiceProviderEntityID());
		AudienceRestriction audienceRestriction = buildSAMLObject(AudienceRestriction.class,
				AudienceRestriction.DEFAULT_ELEMENT_NAME);
		audienceRestriction.getAudiences().add(audience);

		Conditions conditions = buildSAMLObject(Conditions.class, Conditions.DEFAULT_ELEMENT_NAME);
		conditions.getAudienceRestrictions().add(audienceRestriction);
		conditions.setNotBefore(new DateTime().minusMinutes(1));
		conditions.setNotOnOrAfter(new DateTime().plusMinutes(60));
		assertion.setConditions(conditions);

		AuthnStatement authnStatement = buildAuthnStatement(new DateTime(), entityId);

		assertion.setIssuer(issuer);
		assertion.getAuthnStatements().add(authnStatement);

		assertion.getAttributeStatements().add(buildAttributeStatement(principal.getAttributes()));

		assertion.setID(randomSAMLId());
		assertion.setIssueInstant(new DateTime());

		return assertion;
	}

	public static void signAssertion(SignableXMLObject signableXMLObject, Credential signingCredential)
			throws MarshallingException, SignatureException {
		Signature signature = buildSAMLObject(Signature.class, Signature.DEFAULT_ELEMENT_NAME);

		signature.setSigningCredential(signingCredential);
		signature.setSignatureAlgorithm(
				Configuration.getGlobalSecurityConfiguration().getSignatureAlgorithmURI(signingCredential));
		signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

		signableXMLObject.setSignature(signature);

		Configuration.getMarshallerFactory().getMarshaller(signableXMLObject).marshall(signableXMLObject);
		Signer.signObject(signature);
	}

	public static Optional<String> getStringValueFromXMLObject(XMLObject xmlObj) {
		if (xmlObj instanceof XSString) {
			return Optional.ofNullable(((XSString) xmlObj).getValue());
		} else if (xmlObj instanceof XSAny) {
			XSAny xsAny = (XSAny) xmlObj;
			String textContent = xsAny.getTextContent();
			if (StringUtils.hasText(textContent)) {
				return Optional.of(textContent);
			}
			List<XMLObject> unknownXMLObjects = xsAny.getUnknownXMLObjects();
			if (!CollectionUtils.isEmpty(unknownXMLObjects)) {
				XMLObject xmlObject = unknownXMLObjects.get(0);
				if (xmlObject instanceof NameID) {
					NameID nameID = (NameID) xmlObject;
					return Optional.of(nameID.getValue());
				}
			}
		}
		return Optional.empty();
	}

	public static String randomSAMLId() {
		return "_" + UUID.randomUUID().toString();
	}

	private static AuthnStatement buildAuthnStatement(DateTime authnInstant, String entityID) {
		AuthnContextClassRef authnContextClassRef = buildSAMLObject(AuthnContextClassRef.class,
				AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
		authnContextClassRef.setAuthnContextClassRef(AuthnContext.PASSWORD_AUTHN_CTX);

//		AuthenticatingAuthority authenticatingAuthority = buildSAMLObject(AuthenticatingAuthority.class,
//				AuthenticatingAuthority.DEFAULT_ELEMENT_NAME);
//		authenticatingAuthority.setURI(entityID);

		AuthnContext authnContext = buildSAMLObject(AuthnContext.class, AuthnContext.DEFAULT_ELEMENT_NAME);
		authnContext.setAuthnContextClassRef(authnContextClassRef);
//		authnContext.getAuthenticatingAuthorities().add(authenticatingAuthority);

		AuthnStatement authnStatement = buildSAMLObject(AuthnStatement.class, AuthnStatement.DEFAULT_ELEMENT_NAME);
		authnStatement.setAuthnContext(authnContext);

		authnStatement.setAuthnInstant(authnInstant);

		return authnStatement;

	}

	private static AttributeStatement buildAttributeStatement(List<SAMLAttribute> attributes) {
		AttributeStatement attributeStatement = buildSAMLObject(AttributeStatement.class,
				AttributeStatement.DEFAULT_ELEMENT_NAME);

		attributes.forEach(
				entry -> attributeStatement.getAttributes().add(buildAttribute(entry.getName(), entry.getValues())));

		return attributeStatement;
	}

	private static Attribute buildAttribute(String name, List<String> values) {
		XSStringBuilder stringBuilder = (XSStringBuilder) Configuration.getBuilderFactory()
				.getBuilder(XSString.TYPE_NAME);

		Attribute attribute = buildSAMLObject(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
		attribute.setName(name);
		attribute.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
		List<XSString> xsStringList = values.stream().map(value -> {
			XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
			stringValue.setValue(value);
			return stringValue;
		}).collect(toList());

		attribute.getAttributeValues().addAll(xsStringList);
		return attribute;
	}

}
