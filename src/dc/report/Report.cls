Class dc.report.Report
{

ClassMethod Print() As %Status
{
 
    set sc = $$$OK
    
    set GW = ##class(%Net.Remote.Gateway).%New()
    set st = GW.%Connect("127.0.0.1", "55555", "IRISAPP",,)
    set proxy = ##class(%Net.Remote.Object).%New(GW,"dc.xdatareports.reportserver.ReportGenerator")
    set pResponse = proxy.toString()
    write pResponse

    Return sc
}

}
