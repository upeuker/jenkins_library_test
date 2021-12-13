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

	def stringMap = "['a':2,'b':4]"
	def map = evaluate(stringMap)
	
	assert map.a == 2
	assert map.b == 4
	
	def stringMapNested = "['foo':'bar', baz:['alpha':'beta']]"
	def map2 = evaluate(stringMapNested)
	
	assert map2.foo == "bar"
	assert map2.baz.alpha == "beta"
	
	def String key = "${JENKINS_URL}"
	def envDefs = props.getProperty(key, config.get("default", "undefined"), "['a':2,'b':4]");
	def envMap = evaluate(envDefs)
	envMap.each{ k, v -> println "${k}:${v}" }
}