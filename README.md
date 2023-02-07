
Conceptual Data Model

```mermaid
erDiagram
    USER ||--o{ FRIEND : has;
    USER ||--|{ SPOT_ACCOUNT : has;
    USER ||--o{ TRANSACTION : perform;
    FRIEND ||--o{ TRANSACTION : perform;
    USER ||--o{ BANK_ACCOUNT : owns;

    USER {
        int id
        string email
        boolean verified
        string firstName
        string lastName
}
SPOT_ACCOUNT {
    int id
    string currency
    double credit
}
FRIEND  {
    int id
    int friendOwner
    int friendTarget
}

BANK_ACCOUNT    {
    int id
    string name
    string iban
    boolean verified
}

TRANSACTION {
    int id
    id sender
    id receiver
    double rawAmount
    double netAmount
    double fee
    datetime dateTime
    boolean processed
}
```

Relational Data Model

```mermaid
erDiagram
    USER ||--o{ FRIENDSHIP : has
    USER ||--|{ SPOT_ACCOUNT : has
    FRIENDSHIP ||--o{ TRANSACTION : has
    USER ||--o{ BANK_ACCOUNT : owns

    USER {
        USER_ID INTEGER[PK]
        EMAIL VARCHAR[20]
        PASSWORD VARCHAR[20]
        VERIFIED BOOLEAN
        FIRST_NAME VARCHAR[20]
        LAST_NAME VARCHAR[20]
}
SPOT_ACCOUNT {
    SPOT_ACCOUNT_ID INTEGER[PF]
    USER_ID INTEGER[FK]
    CURRENCY VARCHAR[3]
    CREDIT FLOAT
}
FRIENDSHIP  {
    FRIENDSHIP_ID INTEGER[PK]
    USER_ID INTEGER[FK]
    FRIEND_ID INTEGER[FK]
}

BANK_ACCOUNT    {
    BANK_ACCOUNT_ID INTEGER[PK]
    USER_ID INTEGER[FK]
    NAME VARCHAR[10]
    IBAN VARCHAR[34]
    VERIFIED BOOLEAN
}

TRANSACTION {
    TRANSACTION_ID INTEGER[PK]
    FRIENDSHIP_ID INTEGER[FK]
    SENT_TO_FRIEND BOOLEAN
    DESCRIPTION VARCHAR[50]
    GROSS_AMOUNT FLOAT
    NET_AMOUNT FLOAT
    FEE FLOAT
    DATE_TIME DATETIME
    PROCESSED BOOLEAN
}
```
