import { NewIssue } from './model/NewIssue';
import { RequestHelper } from './request/RequestHelper';
import { Utils } from './util/Utils';

export class Render {
    private request: RequestHelper = new RequestHelper();

    /**
     * 보드의 이슈들을 모두 제거합니다.
     */
    public clearBoard(): void {
        Array.from(document.getElementsByClassName('issue-card')).forEach((elIssue) => {
            elIssue.remove();
        });
    }

    /**
     * 테이블의 이슈들을 모두 제거합니다.
     */
    public clearTable(): void {
        Array.from(document.getElementsByClassName('row')).forEach((elIssue) => {
            elIssue.remove();
        });
    }

    /**
     * 보드 아이콘 클릭 이벤트
     */
    public viewBoard(): void {
        this.clearBoard();

        document.getElementById('issue-track-body').classList.remove('hide');
        document.getElementById('issue-table').classList.add('hide');
        document.getElementById('edit-issue').classList.add('hide');
        this.rendBoard();
    }

    /**
     * 테이블 아이콘 클릭 이벤트
     */
    public viewTable(): void {
        this.clearTable();

        document.getElementById('issue-table').classList.remove('hide');
        document.getElementById('issue-track-body').classList.add('hide');
        document.getElementById('edit-issue').classList.add('hide');
        this.rendTable();
    }

    /**
     * 테이블의 이슈들을 그립니다.
     */
    public rendTable(): void {
        const successFunc: Function = (issues: NewIssue[]) => {
            issues.forEach((issue: NewIssue) => {
                this.rendIssueIntoTable(issue);
            });
        };

        const errorFunc: Function = (res: Response) => {
            console.log(res);
        }
        this.request.get('/issues', {}, successFunc, errorFunc);
    }

    /**
     * 이슈를 테이블에 그립니다.
     * @param issue 
     */
    public rendIssueIntoTable(issue: NewIssue): void {
        let elRowIssue: any = document.createElement('tr');
        elRowIssue.classList.add('row')
        elRowIssue.setAttribute('data-id', issue.id);
        elRowIssue.setAttribute('data-obj', 'render');
        elRowIssue.setAttribute('data-cmd', 'clickIssueEvent');
        elRowIssue.innerHTML = (document.getElementById('template-table-item') as HTMLTextAreaElement).value;

        elRowIssue.getElementsByClassName('table-issue-title')[0].textContent = issue.title;
        elRowIssue.getElementsByClassName('table-issue-type')[0].textContent = issue.issueType;
        elRowIssue.getElementsByClassName('table-issue-owner')[0].textContent = issue.ownerName;
        elRowIssue.getElementsByClassName('table-issue-reporter')[0].textContent = issue.ownerName;
        elRowIssue.getElementsByClassName('table-issue-version')[0].textContent = issue.versionNames;
        elRowIssue.getElementsByClassName('table-issue-createdAt')[0].textContent = issue.createdAt;

        document.getElementById('issue-table-tbody').appendChild(elRowIssue);
    }

    /**
     * 보드에 이슈들을 그립니다.
     */
    public rendBoard(): void {
        const successFunc: Function = (issues: NewIssue[]) => {
            issues.forEach((issue: NewIssue) => {
                this.rendIssueIntoBoard(issue);
            });
        };

        const errorFunc: Function = (res: Response) => {
             console.log(res);
        }
        this.request.get('/issues', {}, successFunc, errorFunc);
    }

    /**
     * 이슈를 보드에 그립니다.
     * @param issue 
     */
    public rendIssueIntoBoard(issue: NewIssue): void {
        let elIssueCard: any = document.createElement('div');
        elIssueCard.classList.add('issue-card')
        elIssueCard.setAttribute('data-id', issue.id);
        elIssueCard.setAttribute('data-obj', 'render');
        elIssueCard.setAttribute('data-cmd', 'clickIssueEvent');
        elIssueCard.innerHTML = (document.getElementById('template-new-issue') as HTMLTextAreaElement).value;

        elIssueCard.getElementsByClassName('issue-card-title-text')[0].textContent = issue.title;
        elIssueCard.getElementsByClassName('issue-card-type')[0].textContent = issue.issueType;
        elIssueCard.getElementsByClassName('issue-card-user')[0].textContent = issue.ownerName;
        elIssueCard.getElementsByClassName('issue-card-version')[0].textContent = issue.versionNames;
        elIssueCard.getElementsByClassName('issue-card-label')[0].textContent = issue.labelNames;

        document.getElementsByClassName('issue-todo-item-wrap')[0].appendChild(elIssueCard);
    }

    /**
     * 이슈 생성 또는 편집 다이얼로그에서 버전을 클릭하면 추가
     */
    private clickVersionItem(type: string): void {
        const elInputVersion: HTMLInputElement = document.querySelector(`#issue-${type}-version-input`);
        const selectVersion: string = (event.target as HTMLDivElement).textContent;
        let value: string = elInputVersion.value;

        if (!value) {
            value += selectVersion;
        } else {
            value += `,${selectVersion}`;
        }

        elInputVersion.value = value;
        Utils.removeElement(document.querySelector('.issue-version-wrap'));
    }

    /**
     * 이슈 생성 또는 편집 다이얼로그에서 라벨을 클릭하면 추가
     */
    private clickLabelItem(type: string): void {
        const elInputLabel: HTMLInputElement = document.querySelector(`#issue-${type}-label-input`);
        const selectLabel: string = (event.target as HTMLDivElement).textContent;
        let value: string = elInputLabel.value;

        if (!value) {
            value += selectLabel;
        } else {
            value += `,${selectLabel}`;
        }

        elInputLabel.value = value;
        Utils.removeElement(document.querySelector('.issue-label-wrap'));
    }

    /**
     * 이슈 생성 또는 편집 다이얼로그에서 버전을 그립니다.
     * @param versions 
     */
    public rendVersions(versions: any, type: string): void {
        Utils.removeElement(document.querySelector(`.issue-${type}-version-wrap`));

        const elVersionContent: HTMLDivElement = document.createElement('div');
        elVersionContent.classList.add(`issue-${type}-version-wrap`)
        elVersionContent.innerHTML = '';

        versions.forEach((version: any) => {
            const elNewVersion: HTMLDivElement = document.createElement('div');
            elNewVersion.textContent = version.name;
            elNewVersion.classList.add('issue-version-select-item');
            elNewVersion.setAttribute('data-version-id', version.id);
            elNewVersion.addEventListener('click', this.clickVersionItem.bind(this, type));

            elVersionContent.appendChild(elNewVersion);
        });

        document.querySelector(`#issue-${type}-version-input`).parentElement.appendChild(elVersionContent);
    }

    /**
     * 이슈 생성 또는 편집 다이얼로그에서 라벨을 그립니다.
     * @param versions
     */
    public rendLabels(labels: any, type: string): void {
        Utils.removeElement(document.querySelector(`.issue-${type}-label-wrap`));

        const elLabelsContent: HTMLDivElement = document.createElement('div');
        elLabelsContent.classList.add(`issue-${type}-label-wrap`)
        elLabelsContent.innerHTML = '';

        labels.forEach((version: any) => {
            const elNewLabels: HTMLDivElement = document.createElement('div');
            elNewLabels.textContent = version.name;
            elNewLabels.classList.add('issue-label-select-item');
            elNewLabels.setAttribute('data-version-id', version.id);
            elNewLabels.addEventListener('click', this.clickLabelItem.bind(this, type));

            elLabelsContent.appendChild(elNewLabels);
        });

        document.querySelector(`#issue-${type}-label-input`).parentElement.appendChild(elLabelsContent);
    }

    /**
     * 이슈 편집 페이지 오픈
     */
    public clickIssueEvent(): void {
        const id: string = ((event.target as HTMLElement).closest('.issue-card') || (event.target as HTMLElement).closest('.row')).getAttribute('data-id');

        const successFunc: Function = (issue: NewIssue) => {
            const elEditIssue: any = document.getElementById('edit-issue');
            elEditIssue.setAttribute('data-issue-id', id);
            (document.getElementById('issue-edit-title-input') as HTMLInputElement).value = issue.title;
            (document.getElementById('issue-edit-version-input') as HTMLInputElement).value = issue.versionNames.join(',');
            (document.getElementById('issue-edit-owner-select') as HTMLSelectElement).value = issue.ownerName;
            (document.getElementById('issue-edit-env-content') as HTMLTextAreaElement).value = issue.env;
            (document.getElementById('issue-edit-desc-content') as HTMLTextAreaElement).value = issue.description;
            (document.getElementById('issue-edit-label-input') as HTMLInputElement).value = issue.labelNames.join(',');
            elEditIssue.classList.remove('hide');
        };

        const errorFunc: Function = (res: Response) => {
             console.log(res);
        }

        this.request.get(`/issue/${id}`, {}, successFunc, errorFunc);
    }

    public cancelEditIssue(): void {
        const elEditIssue: any = document.getElementById('edit-issue');
        elEditIssue.classList.add('hide');
    }
}