Class dc.report.TestReport Extends dc.report.Report
{

XData Report [ MimeType = application/json ]
{
{
      "title":"Branches", 
      "query":"select * from FCE.Branch", 
      "columns": [
        {
            "field": "address", 
            "title": "Address"
        },
        {
            "field": "phone",
            "title": "Phone"
        }
      ]
   }
}

}
