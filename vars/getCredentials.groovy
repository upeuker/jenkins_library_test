import groovy.yaml.YamlSlurper

def call(Map config = [:]) {
	def content = libraryResource 'config/jenkinsCredentialMappings.yaml'
	def yamlData = new YamlSlurper().parseText(configYaml)

	sh "echo ${yamlData}"	
}

def cfffall(Map config = [:]) {
	
	def content = libraryResource 'config/jenkinsCredentialMappings.properties'
	Properties props = new java.util.Properties()
	
	new java.io.StringReader(content).with { res ->
		try {
			props.load(res)
		} finally {
			res.close()
		}
	}

	return props.getProperty("${JENKINS_URL}", config.get("default", "undefined"));
}