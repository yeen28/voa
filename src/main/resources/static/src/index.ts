import { Factory } from "./Factory";
import { Render } from "./Render";
import { Utils } from "./util/Utils";

document.addEventListener('DOMContentLoaded', () => {
    const factory = new Factory();
    const render: Render = factory.getObj('render');
    render.rendBoard();

    document.addEventListener('click', () => {
        let target: any = event.target;
        if (!!target.closest('.issue-card') || !!target.closest('.row')) {
            target = target.closest('.issue-card') || target.closest('.row');
        }

        const that: string = target.getAttribute('data-obj');
        const cmd: string = target.getAttribute('data-cmd');
    
        if (!that || !cmd) {
            Utils.removeElement(document.querySelector('.issue-new-version-wrap'));
            Utils.removeElement(document.querySelector('.issue-new-label-wrap'));
            Utils.removeElement(document.querySelector('.issue-edit-version-wrap'));
            Utils.removeElement(document.querySelector('.issue-edit-label-wrap'));
            return;
        }

        const obj = factory.getObj(that);
        obj[cmd].call(obj, target);
    });
}, false);