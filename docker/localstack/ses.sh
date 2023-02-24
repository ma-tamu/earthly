#!/bin/sh

echo "SES setup start!"
echo "Verifying SES email..."
aws ses verify-email-identity --email-address dummy@example.com --endpoint-url=http://localstack:4566

echo "Verifyed SES email!"
echo "SES setup done!"
