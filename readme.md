# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### design pattern name: Flyweight

- Flyweight participant name: Flyweight interface in the products subfolder of spfea folder
- FlyweightFactory name: ProductFlyweightFactory class in the products subfolder of spfea folder
- ConcreteFlyweight name: ProductFlyweight class in the products subfolder of spfea folder
- UnsharedConcreteFlyweight name: UnsharedConcreteFlyweight class in the products subfolder of spfea folder


### Too Many Orders
#### design pattern name: Bridge
- Abstraction participant: 
    + Order interface in ordering folder

- AbstractionImpl participants:
    + BusinessOrder class in ordering subfolder of spfea folder
    + PersonalOrder class in ordering subfolder of spfea folder

- Implementor participants: 
    + OrderCategory interface in ordering folder
    + OrderDiscount interface in ordering folder

- ConcreteImplementor participants: 
    + OneOffOrder class in ordering subfolder of spfea folder
    + SubscriptionOrder class in ordering subfolder of spfea folder

    + BulkDiscount class in ordering subfolder of spfea folder
    + FlatRateDiscount class in ordering subfolder of spfea folder


### Bulky Contact Method
#### design pattern name: Chain Of Responsibility

- Handler participant: Contact abstract class in spfea folder
- Concrete Handler participants: 
    + CarrierPigeonContact class in spfea folder 
    + EmailContact class in spfea folder 
    + MailContact class in spfea folder 
    + MerchandiserContact class in spfea folder 
    + PhoneCallContact class in spfea folder 
    + SMSContact class in spfea folder 

- Client: ContactHandler class in spfea folder 


### System Lag
#### design pattern name: Lazy Load (lazy initialization)

- Lazy participant: CustomerImpl class in spfea folder 

### Hard to Compare Products
#### design pattern name: Value Object 

- Value Object participant: ProductImpl class in the products subfolder of spfea folder

### Slow Order Creation
#### design pattern name: Unit of Work

- Unit Of Work participant: UnitOfWork class in spfea folder
- Database participant: TestDatabase class in database folder (only being listed for participant, I did not touch this class)
- Order Controller participant: SPFEAFacade class in spfea folder

## Notes About the Submission# mockito-testing
