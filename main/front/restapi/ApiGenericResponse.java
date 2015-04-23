package front.restapi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ApiGenericResponse
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "status", "message" })
public class ApiGenericResponse {

	/**
	 * Response status (Required)
	 * 
	 */
	@JsonProperty("status")
	private String status;
	/**
	 * Response message
	 * 
	 */
	@JsonProperty("message")
	private String message;
	/**
	 * User id (paid)
	 * 
	 */
	@JsonProperty("user")
	private Long user;
	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * Response status (Required)
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * Response status (Required)
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Response message
	 * 
	 * @return The message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * Response message
	 * 
	 * @param message
	 *            The message
	 */
	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
