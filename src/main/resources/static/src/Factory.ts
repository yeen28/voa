import { Render } from "./Render";
import { IssueManager } from "./IssueManager";

export class Factory {
    private objMap : any = new Map();
    constructor() {
        this.objMap.set('render', new Render());
        this.objMap.set('issueManager', new IssueManager());
    }

    public getObj(that: string) {
        return this.objMap.get(that);
    }
}