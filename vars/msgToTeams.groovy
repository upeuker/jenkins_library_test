import static java.util.Calendar.YEAR

def call(Map config = [:]) {
	
	def message = libraryResource 'teams/message_template.html'

	def today = new Date()
	
	env.buildDate = today.format("dd.MM.yyyy HH:mm")
	
	emailext (
		attachLog : true,
		body : message,
		compressLog : true,
		mimeType : 'text/html',
		subject: 'Build state in Jenkins: ${JOB_NAME}',
		to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
	)
}