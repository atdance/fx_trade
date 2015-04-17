package restapi;

import com.fasterxml.jackson.databind.ObjectMapper;

public class APICommon {

	protected static ObjectMapper mapper = null;
	static {
		mapper = new ObjectMapper();
	}

	public APICommon() {
		super();
	}

}