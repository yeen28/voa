export class Utils {
    static hideElement(el: any): void {
        el.classList.add('hide');
    }
    
    static showElement(el: any): void {
        el.classList.remove('hide');
    }

    static removeElement(el: any): void {
        if (!el) {
            return;
        }

        el.remove();
    }
}