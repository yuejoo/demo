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
