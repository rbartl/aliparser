@Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.21')
@Grab( group= 'javax.xml.bind', module= 'jaxb-api', version= '2.3.0')

import org.cyberneko.html.parsers.SAXParser
import groovy.util.XmlSlurper
import java.text.SimpleDateFormat


class Main {
    static main(args) {
        def cli = new CliBuilder(usage: 'aliparse.groovy [filename]')
        // Create the list of options.
        cli.with {
            h longOpt: 'help', 'Show usage information'
        }
        def options = cli.parse(args)
        if (!options) {
            cli.usage()
            return
        }
        if (options.h) {
            cli.usage()
            return
        }
        if (options.arguments().size() < 1) {
            cli.usage()
            return
        }
        def filename = options.arguments()[0]


        def parser = new SAXParser()

        File alifile1 = new File(filename)
        def page = new XmlSlurper(parser).parse(alifile1)

        def orderlist = []

        def sdf = new SimpleDateFormat("HH:mm MMM. dd yyyy", Locale.ENGLISH)

        def orders = page.'**'.findAll { prod ->
            prod.attributes().class == 'order-head'
        }
        for (int i = 0; i < orders.size(); i++) {
            def orderid = orders[i].TD[1].P[0].SPAN[1]
            def sordertime = orders[i].TD[1].P[1].SPAN[1].text()

            def storename = orders[i].TD[2].P[0].SPAN[1].text()
            def orderamount = orders[i].TD[3].DIV.P[1].text().trim()
            def currency = "NA"
            if (orderamount.contains('$'))
                currency = '$'
            orderamount = orderamount.replaceAll("^. ", "")


            def ordertime = sdf.parse(sordertime)
/*    println orderid
    println sordertime
    println storename
    println currency
    println orderamount
//    println ordertime
*/
            orderlist[i] = [:]
            orderlist[i].orderid = orderid
            orderlist[i].storename = storename
            orderlist[i].currency = currency
            orderlist[i].orderamount = orderamount
            orderlist[i].ordertime = ordertime
        }


        orders = page.'**'.findAll { prod ->
            prod.attributes().class == 'order-body'
        }
        for (int i = 0; i < orders.size(); i++) {
            def prodname = orders[i].TD[0].DIV[1].P[0].A.text()

            orderlist[i].prodname = prodname
        }

        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        orderlist.each {
            print '"' + sdf.format(it.ordertime) + '"'; print ";"
            print '"' + it.orderid + '"'; print ";"
            print '"' + it.storename + '"'; print ";"
            print '"' + it.currency + '"'; print ";"
            print '"' + it.orderamount + '"'; print ";"
            print '"' + it.prodname + '"'; print ";"
            println ""
        }


    }

}
