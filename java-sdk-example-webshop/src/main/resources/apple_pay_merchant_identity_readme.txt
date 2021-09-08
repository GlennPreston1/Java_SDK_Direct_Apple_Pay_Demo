See 
https://developer.apple.com/documentation/apple_pay_on_the_web/apple_pay_js_api/providing_merchant_validation
https://help.apple.com/developer-account/#/dev1731126fb
etc.

Get the merchant identity certificate (from Apple) and the matching private key.
Put these into a .jks (Java KeyStore) file.
Only one entry/line should be in this file, the private key and the imported certificate.
Set a key and a keystore passoword (they can be different).

Suggested tool (private key generation, JKS file creation etc.):
https://keystore-explorer.org/