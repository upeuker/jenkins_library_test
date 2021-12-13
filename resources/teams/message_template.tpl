<!DOCTYPE html>
<head>
  <title>Build report</title>
</head>
<body style="font-family: Arial;">

<h1>${jobName}</h1>
 
<div style="border-style: solid;border-width: thick;padding: 20px;border-color: ${boxColor};background-color: ${boxFill};">

<table>
  <tr><th>Build-Nr:</th><td>${buildId}</td></tr>
  <tr><th>State:</th><td>${state}</td></tr>
  <tr><th>Date of build:</th><td>${date}</td></tr>
</table>

<hr>

<a href="${buildUrl}" style="-webkit-appearance: button;-moz-appearance: button;appearance: button;text-decoration: none;color: black;padding: 20px;" class="button">View build</a>

</div> 
 
</body>