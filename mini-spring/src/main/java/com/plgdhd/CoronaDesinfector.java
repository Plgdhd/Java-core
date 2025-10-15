package com.plgdhd;
import com.plgdhd.interfaces.Announcer;
import com.plgdhd.interfaces.Autowired;
import com.plgdhd.interfaces.Component;
import com.plgdhd.interfaces.Policeman;

@Component
public class CoronaDesinfector {
    @Autowired
    private Announcer announcer;

    @Autowired
    private Policeman policeman;

    public void start(Room room) {
        announcer.announce("Дезинфекция, валите отсюда");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("Дезинфекция окончена, можете вернуться");
    }

    private void disinfect(Room room) {
        System.out.println("Жесткая молитва про изгнание короны");
    }
}