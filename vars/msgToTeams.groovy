import static java.util.Calendar.YEAR

def call(Map config = [:]) {
	
	sh "echo Ausgabe1: ${currentBuild}"
	sh "echo Ausgabe2: ${this.currentBuild.displayName}"
	
	def today = new Date()
	env.buildDate = today.format("dd.MM.yyyy HH:mm")
	
	
	sh "echo ${buildDate}"
	
	def message = libraryResource 'teams/message_template.html'
	
	emailext (
		attachLog : true,
		body : message,
		compressLog : true,
		mimeType : 'text/html',
		subject: 'Build state in Jenkins: ${JOB_NAME}',
		to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
	)
}