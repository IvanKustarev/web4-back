package web4.back;

import org.springframework.stereotype.Component;
import web4.back.dots.Dot;

@Component
public class AreaChecker {
    public boolean checkArea(Dot dot) {
        if(dot.getR() > 0) {
            if (dot.getX() < 0 && dot.getY() < 0) {
                return false;
            } else if (dot.getX() >= 0 && dot.getY() <= 0) {
                if (Math.pow(Math.abs(dot.getX()), 2) + Math.pow(Math.abs(dot.getY()), 2) <= Math.pow(dot.getR(), 2)) {
                    return true;
                } else {
                    return false;
                }
            } else if (dot.getX() >= 0 && dot.getY() >= 0) {
                if (dot.getX() <= dot.getR() / 2 && dot.getY() <= dot.getR()) {
                    return true;
                } else {
                    return false;
                }
            } else if (dot.getX() <= 0 && dot.getY() >= 0) {
                if (dot.getX() + dot.getR() - dot.getY() >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }else {
            if (dot.getX() > 0 && dot.getY() > 0) {
                return false;
            } else if (dot.getX() <= 0 && dot.getY() >= 0) {
                if (Math.pow(Math.abs(dot.getX()), 2) + Math.pow(Math.abs(dot.getY()), 2) <= Math.pow(dot.getR(), 2)) {
                    return true;
                } else {
                    return false;
                }
            } else if (dot.getX() <= 0 && dot.getY() <= 0) {
                if (dot.getX() >= dot.getR() / 2 && dot.getY() >= dot.getR()) {
                    return true;
                } else {
                    return false;
                }
            } else if (dot.getX() >= 0 && dot.getY() <= 0) {
                if (dot.getX() + dot.getR() - dot.getY() <= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
