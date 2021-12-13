def call(Map config = [:]) {
	mail body: "Check console output at ${BUILD_URL} to view the results.", subject: "Build state in Jenkins: ${JOB_NAME}", to: 'e3a11d4d.aptiv.com@amer.teams.ms'
	
	def message = libraryResource 'teams/successMessage.html'
	mail body: message, subject: "Build state in Jenkins: ${JOB_NAME}", to: 'e3a11d4d.aptiv.com@amer.teams.ms, dev@upeuker.net'
	
}