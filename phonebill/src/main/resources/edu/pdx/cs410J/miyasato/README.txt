This is a README file!

CS410J Project 3 : Pretty Printing A Phone Bill
Forbes Miyasato

The Phone Bill Application reads in a series of options and arguments with information regarding the phone bill and
phone call, and parses the data in order to present the user with a description of the PhoneCall and store PhoneBills.

usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>
args are (in this order):
customer Person whose phone bill we’re modeling
callerNumber Phone number of caller
calleeNumber Phone number of person who was called
start Date and time (am/pm) call began
end Date and time (am/pm) call ended
options are (options may appear in any order):
-pretty file Pretty print the phone bill to a text file
or standard out (file -).
-textFile file Where to read/write the phone bill
-print Prints a description of the new phone call
-README

The command line inputs must follow the interface above.

ex:
input: java -jar target/phonebill-Summer2020.jar -print Forbes 808-200-6188 808-200-2000 1/1/2020 11:39 am \
01/2/2020 1:03 pm

output:  Phone call from 808-200-6188 to 808-200-2000 from 1/15/2020 19:39 to 01/2/2020 1:03
