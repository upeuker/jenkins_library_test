def String getReceivers(String jobName) {
	
//	def content = libraryResource 'config/jobToTeamsMap.properties'
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
//	return props.getProperty(jobName, "info@peuker-online.de")
	
	def searchKey = jobName.replaceAll(' ', '_')
	
	println "Suche nach: " + searchKey
	
	def content = libraryResource 'config/jobToTeamsMap.properties'
	def String[]lines = content.split('\n') 
	for (line in lines) {
		def String[] param = line.split('=')
		if(param.length == 2) {
			def key = param[0].trim()
			
			println "Key: " + key
			
			def value = param[1].trim();
			
			if(key.equals(searchKey)) {
				return value;
			}
		}
	}
	
	return "uwe.peuker@bitctrl.de"
}


def String fillTemplate() {
	
	def resultClasses = ["SUCCESS":"success", "UNSTABLE": "unstable", "FAILURE" : "failure"]
	def resultColors = ["SUCCESS":"green", "UNSTABLE": "yellow", "FAILURE" : "red"]
	def resultBkgds = ["SUCCESS":"lightgreen", "UNSTABLE": "lightyellow", "FAILURE" : "lightred"]
	
	
	def today = new Date()
	env.buildDate = today.format("dd.MM.yyyy HH:mm")
	def String buildState = "${currentBuild.currentResult}"
	def binding = [
		"date":today.format("dd.MM.yyyy HH:mm"),
		"state":buildState.toString(),
		"buildId":"${BUILD_ID}",
		"jobName":"${JOB_NAME}",
		"buildUrl":"${BUILD_URL}",
		"boxClass":resultClasses.get(buildState, "unknown"),
		"boxColor": resultColors.get(buildState, "blue"),
		"boxFill": resultBkgds.get(buildState, "lightblue")
	]
	
	def String message = libraryResource 'teams/message_template.tpl'
	binding.each{k, v -> r:{
			def pattern = '\\$\\{' + k + '\\}'
			message = message.replaceAll(pattern, v)
		}
	}
	
//	def engine = new groovy.text.SimpleTemplateEngine()
//	def message = engine.createTemplate(template).make(binding)
	
	return message.toString()
}

def call(Map config = [:]) {
	
	def attach = config.get("attachLog", "false")
	def compress = config.get("compressLog", "false")
	def subject = config.get("subject", "Build state in Jenkins: ${JOB_NAME}")
	
	message = fillTemplate();

	emailext (
		attachLog : attach,
		body : message,
		compressLog : compress,
		mimeType : 'text/html',
		subject: subject,
		to: getReceivers("${JOB_BASE_NAME}")
//		to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
//		to: 'dev@upeuker.net'
	)
}