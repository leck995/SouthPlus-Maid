package net.southplus.southplusmaid.model.dlsite;

import java.util.List;

public class DLCreater {
    private List<DLActor> created_by;
    private List<DLActor> scenario_by;
    private List<DLActor> illust_by;
    private List<DLActor> voice_by;


    public List<DLActor> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<DLActor> created_by) {
        this.created_by = created_by;
    }

    public List<DLActor> getScenario_by() {
        return scenario_by;
    }

    public void setScenario_by(List<DLActor> scenario_by) {
        this.scenario_by = scenario_by;
    }

    public List<DLActor> getIllust_by() {
        return illust_by;
    }

    public void setIllust_by(List<DLActor> illust_by) {
        this.illust_by = illust_by;
    }

    public List<DLActor> getVoice_by() {
        return voice_by;
    }

    public void setVoice_by(List<DLActor> voice_by) {
        this.voice_by = voice_by;
    }
}
