def call(Map config = [:]) {

//	def  url = new java.net.URL((String)"${JENKINS_URL}")
//
//	def String request = url.getHost()
	
	def url = "${JENKINS_URL}"
	def String request = url[(url.indexOf('://')+ 3)..-1]​.split('/')[0]​
	
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

	return config.get("default")
}