## Models
```
// Site
public interface Site {
  String siteName();
  List<ProductionSchedule> productionSchedules();
}

// ProductionDemand
public interface ProductionDemand {
  String productLine();
  List<Demand> demands();
}

// ProductionSchedule
public interface ProductionDemandWithSchedule {
  String productLine();
  List<Demand> demands();
}

public interface Demand {
  Integer amounts;
  Integer day;
}

```
