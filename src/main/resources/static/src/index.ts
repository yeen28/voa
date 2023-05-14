import { Factory } from "./Factory";
import { Render } from "./Render";

document.addEventListener('DOMContentLoaded', () => {
    const factory = new Factory();
    const render: Render = factory.getObj('render');
    render.rend();

    document.addEventListener('click', () => {
        const target: any = event.target;
        const that: string = target.getAttribute('data-that');
        const cmd: string = target.getAttribute('data-cmd');

        if (!that || !cmd) {
            console.warn('Not Found cmd or that');
            return;
        }

        const obj = factory.getObj(that);
        obj[cmd].call(obj, target);
    });
}, false);