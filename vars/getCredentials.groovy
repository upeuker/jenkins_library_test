def call(Map config = [:]) {
	
	def content = libraryResource 'config/jenkinsEnvMap.properties'
	Properties props = new java.util.Properties()
	
	new java.io.StringReader(content).with { res ->
		try {
			props.load(res)
		} finally {
			res.close()
		}
	}
	
	def java.net.URL url = "${JENKINS_URL}"
	
	def String request = url.getHost()
	def String key = config.get("key", "");
	if(!key.isEmpty()) {
		request = request + "_" + key;
	}
	
	echo "Search for ${request}"
	
	return props.getProperty(request, config.get("default"));
}