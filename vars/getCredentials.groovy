import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;

def createGlobalEnvironmentVariables(String key, String value){

	   Jenkins instance = Jenkins.getInstance();

	   DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties = instance.getGlobalNodeProperties();
	   List<EnvironmentVariablesNodeProperty> envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);

	   EnvironmentVariablesNodeProperty newEnvVarsNodeProperty = null;
	   EnvVars envVars = null;

	   if ( envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0 ) {
		   newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
		   globalNodeProperties.add(newEnvVarsNodeProperty);
		   envVars = newEnvVarsNodeProperty.getEnvVars();
	   } else {
		   envVars = envVarsNodePropertyList.get(0).getEnvVars();
	   }
	   envVars.put(key, value)
	   instance.save()
}


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
	def envDefs = props.getProperty(key, "['a':2,'b':4]");
	def envMap = evaluate(envDefs)
	envMap.each{ k, v -> println "${k}:${v}" }
	envMap.each{k, v -> createGlobalEnvironmentVariables(k.toString(),v.toString())}
	
}