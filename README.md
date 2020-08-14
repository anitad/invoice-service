# invoice-service

## Rest Service with below features
- Create and Get Invoice API
- Get Analytics API
- Send Email API
- Persistent data store
- SendGrid email integration 

## Tech stack
- Spring Boot
- Jersey
- Hibernate
- JUnit
- Mockito
- SendGrid
- Log4J

## DB Schema

![DB schema](/images/dbschema.png)

## API Spec
### Invoice
- Create invoice `POST /v1/invoices`
- Get invoice `GET /v1/invoices/{invoiceId}`
- Get all invoices `GET /v1/invoices`
### Analytics
- Get analytics `GET /v1/alaytics`
- Get analytics for invoices `GET /v1/analytics/invoices/`
Get analytics for products 	`GET /v1/anaytics/products`
- Get analytics for emails 	`GET /v1/analytics/emails`
### Emails
- Send email `PUT /v1/emails/`
- Send email for an invoice `PUT /v1/emails?invoiceId={invId}`

## High level design
![Architecture](/images/service-design.png)
