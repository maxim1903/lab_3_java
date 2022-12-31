import java.util.HashMap;

/**
 * Этот класс хранит основные состояния необходимые для алгоритмма A*
 * чтобы вычислить путь на карте.
 * Имеются окрытые и закрытые состояния в добавок этот класс производит
 * основные операции для поиска пути.
 **/
public class AStarState
{
    /**
     * Ссылка на карту по которорй происходит навигация алгорит ма А*
     **/
    private final Map2D map;
    HashMap<Location, Waypoint> openedW = new HashMap<>();
    HashMap<Location, Waypoint> closedW = new HashMap<>();


    /**
     * Инициализируйте новый объект состояния для использования алгоритма поиска пути A*.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * метод сканирует HAshMap открытых Waypoint и возвращает минимальную общую стоимость
     * а в противном случае если в HashMap нет открытых Waypoint возвращает null
     **/
    public Waypoint getMinOpenWaypoint()
    {
        Location loc = null;
        float minTotalCost = Float.MAX_VALUE;
        for (Location key : openedW.keySet()) {
            float totalCost = openedW.get(key).getTotalCost();
            if (totalCost < minTotalCost) {
                minTotalCost = totalCost;
                loc = key;
            }
        }
        return openedW.get(loc);
    }

    /**
     * Этот метод добавляет (и потенциально обновлянет WayPoint который уже был в HashMap )
     * WayPoint в коллекцию открытых WayPoint. Если ещё нет открытого WayPoint
     * в локации нового WayPoint, тогда новый WayPoint просто добавляется в коллекцию.
     * Если уже есть WayPoint в локации нового WayPoint, то новый заменяет старый
     * только если предыдущая стоимость нового WayPoint меньше чем предыдущая стоимость предыдущего
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location loc = newWP.getLocation();
        if (!openedW.containsKey(loc) || newWP.getPreviousCost() < openedW.get(loc).getPreviousCost()) {
            openedW.put(loc, newWP);
            return true;
        }
        return false;
    }


    /**
     * Возвращает текущее число открытых WayPoint.
     **/
    public int numOpenWaypoints()
    {
        return openedW.size();
    }


    /**
     * Этот метод перемещает WayPoint на заданной локации из открытого списка в закрытый.
     **/
    public void closeWaypoint(Location loc)
    {
        closedW.put(loc, openedW.get(loc));
        openedW.remove(loc);
    }

    /**
     * Возвращает true если в коллекции закрытых WayPoint содержится WayPoint
     * с заданной локацией
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closedW.containsKey(loc);
    }
}