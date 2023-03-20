
package br.com.sbk.saml2.idp.model.spconfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ENTITYID",
    "SPURL",
    "SPATTRIBUTES",
    "SPCERTIFICATE",
    "SPERRO",
    "SPREDIRECT"
})
public class Spconfig implements Serializable
{

    @JsonProperty("ENTITYID")
    private String entityid;
    @JsonProperty("SPURL")
    private String spurl;
    @JsonProperty("SPATTRIBUTES")
    private String spattributes;
    @JsonProperty("SPCERTIFICATE")
    private String spcertificate;
    @JsonProperty("SPERRO")
    private String sperro;
    @JsonProperty("SPREDIRECT")
    private String spredirect;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -7493811599001033695L;

    @JsonProperty("ENTITYID")
    public String getEntityid() {
        return entityid;
    }

    @JsonProperty("ENTITYID")
    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @JsonProperty("SPURL")
    public String getSpurl() {
        return spurl;
    }

    @JsonProperty("SPURL")
    public void setSpurl(String spurl) {
        this.spurl = spurl;
    }

    @JsonProperty("SPATTRIBUTES")
    public String getSpattributes() {
        return spattributes;
    }

    @JsonProperty("SPATTRIBUTES")
    public void setSpattributes(String spattributes) {
        this.spattributes = spattributes;
    }

    @JsonProperty("SPCERTIFICATE")
    public String getSpcertificate() {
        return spcertificate;
    }

    @JsonProperty("SPCERTIFICATE")
    public void setSpcertificate(String spcertificate) {
        this.spcertificate = spcertificate;
    }

    @JsonProperty("SPERRO")
    public String getSperro() {
        return sperro;
    }

    @JsonProperty("SPERRO")
    public void setSperro(String sperro) {
        this.sperro = sperro;
    }

    @JsonProperty("SPREDIRECT")
    public String getSpredirect() {
        return spredirect;
    }

    @JsonProperty("SPREDIRECT")
    public void setSpredirect(String spredirect) {
        this.spredirect = spredirect;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Spconfig.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("entityid");
        sb.append('=');
        sb.append(((this.entityid == null)?"<null>":this.entityid));
        sb.append(',');
        sb.append("spurl");
        sb.append('=');
        sb.append(((this.spurl == null)?"<null>":this.spurl));
        sb.append(',');
        sb.append("spattributes");
        sb.append('=');
        sb.append(((this.spattributes == null)?"<null>":this.spattributes));
        sb.append(',');
        sb.append("spcertificate");
        sb.append('=');
        sb.append(((this.spcertificate == null)?"<null>":this.spcertificate));
        sb.append(',');
        sb.append("sperro");
        sb.append('=');
        sb.append(((this.sperro == null)?"<null>":this.sperro));
        sb.append(',');
        sb.append("spredirect");
        sb.append('=');
        sb.append(((this.spredirect == null)?"<null>":this.spredirect));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.sperro == null)? 0 :this.sperro.hashCode()));
        result = ((result* 31)+((this.spredirect == null)? 0 :this.spredirect.hashCode()));
        result = ((result* 31)+((this.spcertificate == null)? 0 :this.spcertificate.hashCode()));
        result = ((result* 31)+((this.entityid == null)? 0 :this.entityid.hashCode()));
        result = ((result* 31)+((this.spurl == null)? 0 :this.spurl.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.spattributes == null)? 0 :this.spattributes.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Spconfig) == false) {
            return false;
        }
        Spconfig rhs = ((Spconfig) other);
        return ((((((((this.sperro == rhs.sperro)||((this.sperro!= null)&&this.sperro.equals(rhs.sperro)))&&((this.spredirect == rhs.spredirect)||((this.spredirect!= null)&&this.spredirect.equals(rhs.spredirect))))&&((this.spcertificate == rhs.spcertificate)||((this.spcertificate!= null)&&this.spcertificate.equals(rhs.spcertificate))))&&((this.entityid == rhs.entityid)||((this.entityid!= null)&&this.entityid.equals(rhs.entityid))))&&((this.spurl == rhs.spurl)||((this.spurl!= null)&&this.spurl.equals(rhs.spurl))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.spattributes == rhs.spattributes)||((this.spattributes!= null)&&this.spattributes.equals(rhs.spattributes))));
    }

}
