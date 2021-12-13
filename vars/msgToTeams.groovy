import com.cloudbees.groovy.cps.NonCPS
// import static java.util.Calendar.YEAR

@NonCps
def String fillTemplate() {
	
	def today = new Date()
	env.buildDate = today.format("dd.MM.yyyy HH:mm")
	def String buildState = "${currentBuild.currentResult}"
	def binding = [
		"date":today.format("dd.MM.yyyy HH:mm"),
		"state":buildState.toString(),
		"buildId":"${BUILD_ID}",
		"jobName":"${BUILD_URL}",
		"buildUrl":buildUrl.toString(),
		"boxClass":resultClasses.get(buildState.toString(), "unknown")
	]
	
	def template = libraryResource 'teams/message_template.html'
	def engine = new groovy.text.SimpleTemplateEngine()
	def message = engine.createTemplate(template).make(binding)
	
	return message.toString()
}

def call(Map config = [:]) {
	
	def resultClasses = ["SUCCESS":"success", "UNSTABLE": "unstable", "FAILURE" : "failure"]
	
	def attach = config.get("attachLog", "false")
	def compress = config.get("compressLog", "false")
	def subject = config.get("subject", "Build state in Jenkins: ${JOB_NAME}")
	
	sh "echo Ausgabe1: ${currentBuild}"
	sh "echo Ausgabe2: ${currentBuild.displayName}"
	sh "echo Ausgabe3: ${currentBuild.result}"
	
	message = fillTemplate();

	emailext (
		attachLog : attach,
		body : message,
		compressLog : compress,
		mimeType : 'text/html',
		subject: subject,
//		to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
		to: 'dev@upeuker.net'
	)
}