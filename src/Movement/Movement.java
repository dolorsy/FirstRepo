package Movement;

import utility.Point;

public interface Movement {
    Point GetNextPoint(Point concurrent);
}
