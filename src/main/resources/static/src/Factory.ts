import { Render } from "./Render";
import { Issue } from "./Issue";

export class Factory {
    private objMap : any = new Map();
    constructor() {
        this.objMap.set('render', new Render());
        this.objMap.set('issue', new Issue());
    }

    public getObj(that: string) {
        return this.objMap.get(that);
    }
}