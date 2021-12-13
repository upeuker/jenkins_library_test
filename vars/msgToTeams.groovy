import static java.util.Calendar.YEAR

def call(Map config = [:]) {
	
	sh "echo Ausgabe1: ${currentBuild}"
	sh "echo Ausgabe2: ${currentBuild.displayName}"
	sh "echo Ausgabe3: ${currentBuild.result}"
	
	def today = new Date()
	env.buildDate = today.format("dd.MM.yyyy HH:mm")
	
	
	sh "echo ${buildDate}"
	
	def binding = [
		"date":today.format("dd.MM.yyyy HH:mm"), 
		"state":"${currentBuild.result}",
		"buildId":"${BUILD_ID}"
		"jobName":"${JOB_NAME}"
		"buildUrl":"${BUILD_URL}"
	]
	def template = libraryResource 'teams/message_template.html'
	def engine = new groovy.text.SimpleTemplateEngine()
	def message = engine.createTemplate(template).make(binding).toString()
	
	emailext (
		attachLog : true,
		body : message,
		compressLog : true,
		mimeType : 'text/html',
		subject: 'Build state in Jenkins: ${JOB_NAME}',
		to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
	)
}