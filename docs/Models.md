## Parsing 
Transfer the table:
```
site,product,day,demand
0032,P001A,0,0
0032,P001A,1,0
0032,P001A,2,0
```
to Map<SiteName, Map<ProductName, ProductionLine>>


Map<SiteName, Map<ProductName, ProductionLine>> 
to 
List<Sites>
 
## Models
```
// Site
public interface Site {
  String siteName();
  List<ProductionLine> productionSchedules();
}

// ProductionLine with key days
public interface ProductionLine {
  String productName();
  List<KeyDates> keyDates();
} 

// ProductionLine with accumulated demands
public interface ProductionLineWithDemands {
  String productLine();
  List<Demand> demands();
  List<KeyDayWithDemand> keyDates();
}

// ProductionLine with produce amount
public interface ProductionLineWithProduce {
  String productLine();
  List<Demand> demands();
  List<Produce> produces();
  List<KeyDates> keyDates();
}

public interface Demand {
    Amount amount();
    Day day();
}

public interface KeyDayWithDemand {
    Day day();
    Demand demand();
}

public interface Amount {
    int getValue();
}

public interface Day {
    int value();
}
```
