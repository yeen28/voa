import { Factory } from "./Factory";
import { Render } from "./Render";

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
            document.getElementById('issue-version-select').classList.add('hide');
            document.getElementById('issue-label-select').classList.add('hide');
            return;
        }

        const obj = factory.getObj(that);
        obj[cmd].call(obj, target);
    });
}, false);