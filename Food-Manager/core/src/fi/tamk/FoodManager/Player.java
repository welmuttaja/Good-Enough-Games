package fi.tamk.FoodManager;

class Player {
    private float energy;
    private float weight;
    private float healthiness;
    private float happiness;
    private float money;

    public Player(float en, float we, float he, float ha, float m){
        this.energy = en;
        this.weight = we;
        this.healthiness = he;
        this.happiness = ha;
        this.money = m;
    }

    public void updateStats(){
        if(this.energy > 0 && this.weight > 0 && this.healthiness > 0 && this.happiness > 0) {
            this.energy -= 0.0002f;
            this.weight -= 0.0002f;
            this.healthiness -= 0.0002f;
            this.happiness -= 0.0002f;
        }

    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {

        this.energy = energy;

        if(this.energy > 1) {
            this.energy = 1;
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {

        this.weight = weight;

        if(this.weight > 1) {
            this.weight = 1;
        }
    }

    public float getHealthiness() {
        return healthiness;
    }

    public void setHealthiness(float healthiness) {

        this.healthiness = healthiness;

        if(this.healthiness > 1) {
            this.healthiness = 1;
        }
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {

        this.happiness = happiness;

        if(this.happiness > 1) {
            this.happiness = 1;
        }
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}