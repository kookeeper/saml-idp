<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html dir="ltr"
	class="sapUiTheme-sap_bluecrystal sap-desktop sapUiMedia-Std-Desktop sapUiMedia-StdExt-LargeDesktop"
	data-sap-ui-browser="cr72" data-sap-ui-os="win10"
	data-sap-ui-animation="on" data-sap-ui-animation-mode="full"
	style="height: 100%;" lang="pt-BR">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="js/avance/prompt.js"></script>
<script src="js/avance/runScript.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="minimum-scale=0.25, maximum-scale=4.0, user-scalable=yes">
<meta http-equiv="expires" content="0">
<meta name="msapplication-config" content="none">

<script src="js/avance/XMLHttpRequest.js" type="text/javascript"></script>
<script src="js/avance/perflog_ad8aee309a7f07faba94049906b78e48.js"
	type="text/javascript"></script>
<script>
	!function(i, n, o, a, f, r) {
		function c(i, n, o) {
			if (n = i[n] = i[n] || {}, o)
				for ( var a in o)
					n[a] = o[a]
		}
				c(i, n),
				c(
						i[n],
						'resourceRoots',
						{
							
	sap.sf.surj.shell" : "/ui/surj/resources_44AA2864686C8350261493B953BAF418/sap/sf/surj/shell/",
							"sap.sf.digitalexperience" : "/ui/digitalexperience/resources_A1DCC921D439AB44A54AD19522A74F39/sap/sf/digitalexperience/",
							"sap.sf.quickcard" : "/ui/todo/resources_7CCDD4498D484C11345325D5F8FA14D7/sap/sf/todo/",
							"sap.sf.todo" : "/ui/todo/resources_7CCDD4498D484C11345325D5F8FA14D7/sap/sf/todo/",
							"sap.sf.surj.commons" : "/ui/surj/resources_20B6CD8236B0C87D5715E3B860A0A4CA/sap/sf/surj/commons/"
						}),
				c(
						i,
						'_pathMappings',
						{
							"sap/sf/quickcard/i18n/messagebundle.properties" : "/messagebundle/_/vmod_4eea5598/ui/quickcard/resources/sap/sf/quickcard/i18n/messagebundle.properties",
							"sap/sf/peopleprofile/i18n/messagebundle.properties" : "/messagebundle/_/vmod_4eea5598/ui/peopleprofile/resources/sap/sf/peopleprofile/i18n/messagebundle.properties",
							"sap/sf/surj/commons/i18n/messagebundle.properties" : "/messagebundle/_/vmod_90eeaa6/ui/surj/resources/sap/sf/surj/commons/i18n/messagebundle.properties",
							"sap/sf/surj/shell/i18n/messagebundle.properties" : "/messagebundle/_/vmod_90eeaa6/ui/surj/resources/sap/sf/surj/shell/i18n/messagebundle.properties",
							"sap/employeecentral/timemanagement/workschedule/i18n/messagebundle.properties" : "/messagebundle/_/vmod_2be96c7e/ui/timemanagement/resources/sap/employeecentral/timemanagement/workschedule/i18n/messagebundle.properties",
							"sap/sf/todo/i18n/messagebundle.properties" : "/messagebundle/_/vmod_a307222d/ui/todo/resources/sap/sf/todo/i18n/messagebundle.properties"
						})
	}(window, 'sap-ui-config');
</script>

<meta name="robots" content="noindex, nofollow">
<meta id="PerfLog.eventId"
	content="EVENT-PLT-LOGINPAGE-lnF08b063db2Fcb08FrbbrFrboFbnqo-20190802182206-2018296">
<meta id="PerfLog.usePageRequestStartTime" content="false">
<meta
	id="resourcemapping:/ui/login/resources_676ECB01C15B463508C6A22B769C537F/sap/sf/login/i18n/messagebundle.properties"
	content="/messagebundle/_/vmod_90eeaa6/ui/login/resources/sap/sf/login/i18n/messagebundle.properties">
<title>Login GPA</title>

<style type="text/css">
body {
	visibility: hidden;
}
</style>

<link rel="stylesheet" href="css/avance/library.css"
	id="sap-ui-theme-sap.ui.core" data-sap-ui-ready="false">
<link rel="stylesheet" href="css/avance/library_005.css"
	data-sap-ui-ready="true"
	data-sap-ui-foucmarker="sap-ui-theme-sap.ui.core">
<link rel="stylesheet" href="css/avance/library.css"
	id="sap-ui-theme-sap.m" data-sap-ui-ready="false">
<link rel="stylesheet" href="css/avance/library_002.css"
	data-sap-ui-ready="true" data-sap-ui-foucmarker="sap-ui-theme-sap.m">
<link rel="stylesheet" href="css/avance/library.css"
	id="sap-ui-theme-sap.sf" data-sap-ui-ready="false">
<link rel="stylesheet" href="css/avance/library_004.css"
	data-sap-ui-ready="true" data-sap-ui-foucmarker="sap-ui-theme-sap.sf">
<link rel="stylesheet" href="css/avance/library.css"
	id="sap-ui-theme-sap.ui.layout" data-sap-ui-ready="false">
<link rel="stylesheet" href="css/avance/library_003.css"
	data-sap-ui-ready="true"
	data-sap-ui-foucmarker="sap-ui-theme-sap.ui.layout">
<link rel="stylesheet" href="css/avance/library.css"
	id="sap-ui-theme-sap.sf.surj.shell" data-sap-ui-ready="true">
<style type="text/css">
@font-face {
	font-family: 'SAP-icons';
	src:
		url('/ui/sapui5/main_1.60.8/sap/ui/core/themes/base/fonts/SAP-icons.woff2')
		format('woff2'),
		url('/ui/sapui5/main_1.60.8/sap/ui/core/themes/base/fonts/SAP-icons.woff')
		format('woff'),
		url('/ui/sapui5/main_1.60.8/sap/ui/core/themes/base/fonts/SAP-icons.ttf')
		format('truetype'), local('SAP-icons');
	font-weight: normal;
	font-style: normal;
}
</style>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="css/default/BizXLoginTheme.css" type="text/css"
	rel="stylesheet" id="globalLoginCss" data-bizx-company="gpabr">
<meta id="perfLogData0"
	content="{&quot;callId&quot;:&quot;6272367880-0&quot;,&quot;eventId&quot;:&quot;EVENT-PLT-LOGINPAGE-lnF08b063db2Fcb08FrbbrFrboFbnqo-20190802182206-2018296&quot;,&quot;jsNum&quot;:&quot;20&quot;,&quot;cssNum&quot;:&quot;8&quot;,&quot;renderTime&quot;:&quot;1211&quot;,&quot;TTB&quot;:&quot;155&quot;,&quot;RSR&quot;:&quot;499&quot;,&quot;DIA&quot;:&quot;2183&quot;}">
<link href="css/default/BizXLogin_877b22373b3af8dfe7040ef59e3baeb1.css"
	type="text/css" rel="stylesheet">
</head>

<body class="login_branded globalThemed" role="application"
	style="height: 100%; visibility: visible;">

	<div id="dynamic_header" style="display: none"></div>

	<div id="bodyContent" data-sap-ui-area="bodyContent"
		style="height: 100%;">
		<div id="__jsview0" data-sap-ui="__jsview0"
			style="width: 100%; height: 100%;"
			class="bizxLogin globalLogin sapUiJSView sapUiView sapUiViewDisplayBlock">
			<div class="globalLoginBgOverlay" data-sap-ui-preserve="__html2"
				id="__html2"></div>
			<div class="dynamic_header" style="display: none;"
				data-sap-ui-preserve="__html0" id="__html0"></div>
			<span class="globalLoginLogoURL" style="display: none"
				data-sap-ui-preserve="__html3" id="__html3"></span>
			<div id="bizXLoginApp" data-sap-ui="bizXLoginApp"
				class="loginApp sapMApp sapMNav sapUiGlobalBackgroundColor"
				style="width: 100%; height: 916px;">
				<div id="bizXLoginApp-BG"
					class="sapMAppBG sapUiGlobalBackgroundImage"></div>
				<div id="login" data-sap-ui="login"
					class="sapMNavItem sapMPage sapMPageBgStandard sapMPageBusyCoversAll">
					<section id="login-cont" class="sapMPageEnableScrolling"
						style="overflow: hidden auto;">
						<div id="__layout9" data-sap-ui="__layout9"
							class="mainAppContent sapUiVlt sapuiVlt">
							<div class="sapUiVltCell sapuiVltCell">
								<div id="__layout8" data-sap-ui="__layout8"
									class="globalLoginLogoContainer sapUiVlt sapuiVlt">
									<div class="sapUiVltCell sapuiVltCell">
										<img id="__image1" data-sap-ui="__image1"
											src="images/gpa-logo.png"
											class="globalLoginLogoImg sapMImg sapMImgFocusable">
									</div>
									<div id="divErro" class="sapUiVltCell sapuiVltCell">
										<font size="2" color="red"><c:out value="${msgErro}" /></font>
									</div>
								</div>
							</div>
							<div class="sapUiVltCell sapuiVltCell"></div>
							<div class="sapUiVltCell sapuiVltCell">
								<form id="loginForm" data-sap-ui="loginForm"
									class="bizLoginForm" method="POST" pages="doSSO"
									autocomplete="off">
									<input type="hidden" name="bplte_userid" value=""> <input
										type="hidden" name="company" value="gpabr">
									<div id="__layout7" data-sap-ui="__layout7"
										class="globalLoginFormInputs loginFormInputs sapUiVlt sapuiVlt">
										<div class="sapUiVltCell sapuiVltCell">
											<div id="__layout5" data-sap-ui="__layout5"
												class="sapUiHLayout sapUiHLayoutNoWrap">
												<div class="sapUiHLayoutChildWrapper">
													<span id="__icon1" data-sap-ui="__icon1"
														role="presentation" aria-hidden="true"
														data-sap-ui-icon-content=""
														class="inputIcon sapUiIcon sapUiIconMirrorInRTL"
														style="font-family: &amp; amp;"> <img
														class="manImg" src="images/person-b.png">
													</span>
												</div>
												<div class="sapUiHLayoutChildWrapper">
													<div id="__input1" data-sap-ui="__input1"
														style="width: 100%"
														class="inputWithHelp sapMInput sapMInputBase sapMInputBaseHeightMargin username">
														<div id="__input1-content"
															class="sapMInputBaseContentWrapper" style="width: 100%">
															<div class="sapMInputBaseDynamicContent">
																<input id="__input1-inner" name="j_username"
																	placeholder="Numero da Matricula"
																	aria-labelledby="__input1-labelledby"
																	x-moz-errormessage=" " type="text"
																	style="border: none !important; background: transparent !important; height: 35px; margin-left: .60rem; margin-right: 0; padding-left: .15rem; padding-right: 0; width: 97%; outline-width: 0;"
																	accesskey="1">
															</div>
														</div>
														<span id="__input1-labelledby" aria-hidden="true"
															class="sapUiInvisibleText">Numero da Matricula</span>
													</div>
												</div>
											</div>
										</div>
										<div class="sapUiVltCell sapuiVltCell">
											<div id="__layout6" data-sap-ui="__layout6"
												class="sapUiHLayout sapUiHLayoutNoWrap">
												<div class="sapUiHLayoutChildWrapper">
													<span id="__icon2" data-sap-ui="__icon2"
														role="presentation" aria-hidden="true"
														data-sap-ui-icon-content=""
														class="inputIcon sapUiIcon sapUiIconMirrorInRTL"
														style="font-family: &amp; amp;"> <img
														class="manImg" src="images/password-b.png">
													</span>
												</div>
												<div class="sapUiHLayoutChildWrapper">
													<div id="__input2" data-sap-ui="__input2"
														style="width: 100%"
														class="inputWithHelp password sapMInput sapMInputBase sapMInputBaseHeightMargin">
														<div id="__input2-content"
															class="sapMInputBaseContentWrapper" style="width: 100%">
															<div class="sapMInputBaseDynamicContent">
																<input id="__input2-inner" name="j_password"
																	placeholder="Senha de Rede" value=""
																	aria-labelledby="__input2-labelledby"
																	x-moz-errormessage=" " type="password"
																	style="border: none !important; background: transparent !important; height: 35px; margin-left: .60rem; margin-right: 0; padding-left: .15rem; padding-right: 0; width: 97%; outline-width: 0;">
															</div>
														</div>
														<span id="__input2-labelledby" aria-hidden="true"
															class="sapUiInvisibleText">Senha de Rede</span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<button id="__button2" data-sap-ui="__button2"
										aria-describedby="__text0"
										class="fullButton sapMBtn sapMBtnBase sapMBtnInverted"
										type="button">
										<span id="__button2-inner"
											class="sapMBtnEmphasized sapMBtnHoverable sapMBtnInner sapMBtnText sapMFocusable"><span
											class="sapMBtnContent" id="__button2-content"> <input
												type="submit"
												style="background: transparent; border: none !important; width: 100%; text-align: center; height: 100%; color: white;"
												tabindex="-1" data-sap-ui-preserve="__html5" id="__html5"></span></span>
									</button>
									<input type="hidden" size="1024" name="originalUrl"
										value="https://servicos-homolog.cbdnet.com.br:4001/saml/login" />
									<input type="hidden" size="1024" name="SAMLRequest"
										value="${samlRequest}" /> <input type="hidden" size="1024"
										name="postRequest" value="${postRequest}" /> <input
										type="hidden" size="1024" name="entityId" value="${entityId}" />
									<input type="hidden" size="1024" name="requestID"
										value="${requestID}" /> <input type="hidden" size="1024"
										name="requestIssuer" value="${requestIssuer}" /> <input
										type="hidden" size="1024" name="relayState"
										value="${relayState}" /> <input type="hidden" size="1024"
										name="sigAlg" value="${sigAlg}" /> <input type="hidden"
										size="1024" name="signature" value="${signature}" />
								</form>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
		<div class="globalLoginFooter bizXFooter"
			data-sap-ui-preserve="__html1" id="__html1">
			<span class="copyrightText">Copyright © <a class="link"
				href="http://www.successfactors.com/"
				onclick="window.open(this.href); return false;">2019
					SuccessFactors, Inc.</a> Todos os direitos reservados. Estes serviços
				on-line são confidenciais e de propriedade da SuccessFactors e para
				uso apenas de clientes autorizados da SuccessFactors.
			</span><a class="versionInfoLink link" href="javascript:void(0);"
				id="version_info_link"
				onclick="sap.sf.login.bizx.BizXLogin.showVersionInfo(this); return false;">Exibir
				informações de versão.</a>
		</div>
	</div>
</body>
</html>