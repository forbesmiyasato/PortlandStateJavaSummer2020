This is a README file!

CS410J Project 1 : Designing a Phone Bill Application

The Phone Bill Application reads in a series of options and arguments with information regarding the phone bill and
phone call, and parses the data in order to present the user with a description of the PhoneCall.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
args are (in this order):
customer Person whose phone bill we’re modeling
callerNumber Phone number of caller
calleeNumber Phone number of person who was called
start Date and time call began (24-hour time)
end Date and time call ended (24-hour time)
options are (options may appear in any order):
-print Prints a description of the new phone call
-README Prints a README for this project and exits
Date and time should be in the format: mm/dd/yyyy hh:mm

The command line inputs must follow the interface above.

ex:
input: java -jar target/phonebill-Summer2020.jar -print Forbes 808-200-6188 808-200-2000 1/15/2020 19:39 01/2/2020 1:03

output:  Phone call from 808-200-6188 to 808-200-2000 from 1/15/2020 19:39 to 01/2/2020 1:03
