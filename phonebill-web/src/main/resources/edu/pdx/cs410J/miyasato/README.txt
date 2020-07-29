This is a README file!

CS410J Project 4 :  A REST-ful Phone Bill Web Service
Forbes Miyasato

The Phone Bill Application's Command Line Client reads in a series of options and arguments with information regarding
the phone bill and phone call, fetches data from the servlet via a rest client, and then parses the data in order to
present the user with a description of the PhoneCall and store PhoneBills. There's also REST access to the Phone Bill
Application that supports different formats of URL in order to retrieve data.

Command line usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>
args are (in this order):
customer Person whose phone bill we’re modeling
callerNumber Phone number of caller
calleeNumber Phone number of person who was called
start Date and time call began
end Date and time call ended
options are (options may appear in any order):
-host hostname Host computer on which the server runs
-port port Port on which the server is listening
-search Phone calls should be searched for
-print Prints a description of the new phone call
-README Prints a README for this project and exits

The command line inputs must follow the interface above.

ex:
input: java -jar target/phonebill-Summer2020.jar -print Forbes 808-200-6188 808-200-2000 1/1/2020 11:39 am \
01/2/2020 1:03 pm

output:  Phone call from 808-200-6188 to 808-200-2000 from 1/15/2020 19:39 to 01/2/2020 1:03
