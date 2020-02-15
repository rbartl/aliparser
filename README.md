# aliparser

some short and quite bad code to convert the HTML order page into a CSV format.
uses groovy and XMLSlurper to read the HTML and parse it.

# example usage

if you have a few orderpages in html format in the current workdirectory e.g.
-rwxrwxrwx 1 dfg 125062 Feb 15 17:08 ali_orders_25.01.2019-30.11.2019.html
-rwxrwxrwx 1 dfg 127394 Feb 15 17:07 ali_orders_30.11.2019-13.02.2020.html

to parse them and convert all of it into html (using bash & groovy)

for I in ali*html ; do groovy aliparser.groovy $I ; done > aliexpresslist.csv

# output

should look something like this 


    "2018/12/22";"97199564824088";"ing Store";"$";"5";"12 v/24 v DC 100KPA Luft Kompressor Mini Vakuumpumpe Vakuum Saug 15L/Min Air Flow";
    "2018/11/30";"96920188554088";"nics Co., Ltd";"$";"3";"Hellotronics 50 Pcs";
    "2018/11/12";"96237892954088";"Store";"$";"2";"High speed microsd ";

# why

using 3 hours to solve a problem in parts that would have taken 20 minutes manually... but those would have been more annoying perhaps.




