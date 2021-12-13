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
	
	def String request = "${JENKINS_URL}"
	def String key = config.get("key");
	if(!key.isEmpty()) {
		request = request + "_" + key;
	}
	
	return props.getProperty(request, config.getDefault("default"));
}