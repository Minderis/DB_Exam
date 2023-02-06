# DB_Exam
Repository for DB Exam source code

# Installation
1. Create mysql schema in your database
2. Change hibernate.cfg.xml file with your settings (database name, username, for example: localhost:3306/your_schema_name)
3. Add your database password to hibernate.properties file (hibernate.connection.password=your_password)
4. Run the program

Note: database structure and test data can be imported from provided dumps (structure_only, data_only, full) or it will be automaticaly created by entering first time "[1] - Start exam" section in app.

# Menu tree
Choose action:
	[1] - Start exam
		[x] - Exam theme title
		[x] - Exam theme title
		...
		[*] - return to main menu
	[2] - Create new question
		Pickup from list theme for your question or create new one theme:
		[x] - Exam theme title
		[x] - Exam theme title
		...
		[x] - create new theme
		[*] - return to main menu
	[3] - Edit question
		Pickup from list theme to get list of questions:
		[x] - Exam theme title
		[x] - Exam theme title
		...
		[*] - return to main menu
	[4] - Statistics
		[1] - Count all taken exams
		[2] - Count all taken exams by user
		[3] - Count average score of correct answers
		[4] - Count average score of correct answers by exams
		[5] - Show choices statistics
		[*] - return to main menu
	[*] - Quit program

