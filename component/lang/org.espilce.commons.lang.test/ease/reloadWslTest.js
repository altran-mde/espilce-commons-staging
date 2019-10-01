var reportFileUrl = "file:/C:/Users/nstotz/git/espilce-commons/component/lang/org.espilce.commons.lang.test/target/reports/TEST-junit-jupiter.xml"

loadModule("/System/Platform")
loadModule("/System/UI")

function executePaste() {
	executeCommand('org.eclipse.ui.edit.paste')	
}

var oldContents = getClipboard()
var oldView = getActiveView()

showView("JUnit")
setClipboard(reportFileUrl)
executeUI("executePaste()")

setClipboard(oldContents)
