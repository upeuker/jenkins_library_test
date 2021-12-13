def call(Map config = [:]) {

	//	def content = libraryResource 'config/jenkinsEnvMap.properties'
	//	Properties props = new java.util.Properties()
	//
	//	new java.io.StringReader(content).with { res ->
	//		try {
	//			props.load(res)
	//		} finally {
	//			res.close()
	//		}
	//	}
	//
	//	def  url = new java.net.URL((String)"${JENKINS_URL}")
	//
	//	def String request = url.getHost()
	//	def String key = config.get("key", "");
	//	if(!key.isEmpty()) {
	//		request = request + "_" + key;
	//	}
	//
	//	echo "Search for ${request}"
	//
	//	return props.getProperty(request, config.get("default"));

	def  url = new java.net.URL((String)"${JENKINS_URL}")

	def String request = url.getHost()
	def String key = config.get("key", "");
	if(!key.isEmpty()) {
		request = request + "_" + key;
	}

	echo "Search for ${request}"

	def content = libraryResource 'config/jenkinsEnvMap.properties'
	def String[]lines = content.split('\n')
	for (line in lines) {
		def String[] param = line.split('=')
		if(param.length == 2) {
			def paramKey = param[0].trim()
			def value = param[1].trim();

			if(paramKey.equals(request)) {
				return value;
			}
		}
	}

	return "info@peuker-online.de"
}