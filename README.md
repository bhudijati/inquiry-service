# inquiry-service
http://localhost:8080/beneficiary/api/swagger-ui.html#/inquiry-controller


Sample req:
{
  "amount": 200000,
  "bankCode": "021",
  "destAccount": "123123123",
  "sourceAccount": "100041201001"
}

Sample Resp

{
  "responseCode": "INT:0002",
  "errorDescEn": "Invalid Destination",
  "errorDescId": "Invalid Destination",
  "referenceNumber": "5f0cb128-77b1-4e9f-9c2d-fa5b6281d1d5"
}

{
  "responseCode": "INT:0001",
  "errorDescEn": "Invalid amount",
  "errorDescId": "Invalid amount",
  "referenceNumber": "e09c21d1-3946-4158-a5b1-95f43f704f95"
}

{
  "destinationAccountNumber": "123456789",
  "destinationAccountName": "Mocking Desti",
  "amount": 1551252,
  "adminFee": 6500,
  "referenceNumber": "3f9e50a6-2e3c-4523-8123-7367db5cf22f"
}
