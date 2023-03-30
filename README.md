
**Conceptual Data Model**

![](Conceptual%20Data%20Model.png)

**Relational Data Model**

![](Relational%20Data%20Model.png)

Alternatively, paste this into mermaid.live:

```mermaid
erDiagram
    USER ||--o{ RECIPIENT : "has"
    USER ||--|{ SPOT_ACCOUNT : owns
    RECIPIENT ||--o{ PAYMENT : receives
    USER ||--o{ BANK_ACCOUNT : owns
    BANK_ACCOUNT ||--o{ OPERATION : authorizes
    SPOT_ACCOUNT ||--o{ OPERATION : enables

    USER {
        USER_ID INT[PK]
        EMAIL VARCHAR[40]
        PASSWORD VARCHAR[20]
        FIRST_NAME VARCHAR[20]
        LAST_NAME VARCHAR[20]
        VERIFIED BOOLEAN
        ENABLED BOOLEAN
}
SPOT_ACCOUNT {
    SPOT_ACCOUNT_ID INT[PF]
    USER_ID INT[FK]
    CURRENCY VARCHAR[3]
    CREDIT FLOAT
    ENABLED BOOLEAN
}
RECIPIENT  {
    RECIPIENT_ID INT[PK]
    RECIPIENT_USER_ID INT[FK]
    USER_ID INT[FK]
    ENABLED BOOLEAN
}

BANK_ACCOUNT    {
    BANK_ACCOUNT_ID INT[PK]
    USER_ID INT[FK]
    NAME VARCHAR[20]
    IBAN VARCHAR[34]
    VERIFIED BOOLEAN
    ENABLED BOOLEAN
}

PAYMENT {
    PAYMENT_ID INT[PK]
    RECIPIENT_ID INT[FK]
    DESCRIPTION VARCHAR[50]
    GROSS_AMOUNT FLOAT
    NET_AMOUNT FLOAT
    FEE_PERCENT FLOAT
    CURRENCY VARCHAR[3]
    DATE_TIME DATETIME
    PROCESSED BOOLEAN
}

OPERATION   {
    OPERATION_ID INT[PK]
    BANK_ACCOUNT_ID INT[FK]
    SPOT_ACCOUNT_ID INT[FK]
    DATE_TIME DATETIME
    AMOUNT FLOAT
    WITHDRAWAL BOOLEAN
    PROCESSED BOOLEAN
}
```
