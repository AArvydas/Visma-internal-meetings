# Internal-meetings
Meetings Management Application This is a web application built using Java Spring Boot that allows for the management of internal meetings at Visma.

Features The application provides the following REST API endpoints for meeting management:

Create a new meeting: Creates a new meeting with the specified details. All meeting data is stored in a JSON file, ensuring persistence between restarts. The meeting model includes properties such as name, responsible person, description, category, type, start date, and end date.

Delete a meeting: Deletes a meeting from the system. Only the person responsible for the meeting can perform this action.

Add a person to a meeting: Adds a person to a specific meeting. The command specifies the person to be added and the time of addition. If the person is already part of another meeting that overlaps with the one being added, a warning message is displayed. Additionally, the same person cannot be added twice to the same meeting.

Remove a person from a meeting: Removes a person from a meeting. However, the person responsible for the meeting cannot be removed.

List all meetings: Retrieves a list of all meetings.
