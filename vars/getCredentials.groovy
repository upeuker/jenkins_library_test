def call(Map config = [:]) {

//	def  url = new java.net.URL((String)"${JENKINS_URL}")
//
//	def String request = url.getHost()
	
	def String url = "${JENKINS_URL}"
	url = url.replaceAll('http?://', '')
	url = url.split('/')[0]
	url = url.split(':')[0]
	
	println "URL (" + url.getClass() + ") " + url
//	println parts
//	println parts[0] + " --> " + parts[0].getClass()
//	println parts[1]
//	def String request = parts[0]​.toString().trim()
	
	def String request = url
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