<script>
	import { Label, Input, Checkbox, Button } from 'flowbite-svelte';
	import { setCookie, getCookie } from '../../utils/CookieUtils.js';

	let loginInput = {
		email: '',
		password: ''
	}

	function login() {
		if (loginInput.email === '' || loginInput.password === '') {
			alert('이메일 또는 비밀번호를 입력해 주세요.');
			return;
		}

		fetch('/login/user', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({'email':loginInput.email, 'password':loginInput.password})
		})
			.then(response => {
				if (!response.ok) {
					alert('아이디 비밀번호가 올바르지 않습니다.');
					return;
				}

				setCookie('token', response.headers.get('Authorization'), 30);
				return response.json();
			})
			.then(response => {
				if (response) {
					fetch('/main', {
						method: 'GET',
						headers: {
							'Authorization': getCookie('token')
						}
					})
				}
			})
			.catch(e => console.log(e));
	}
</script>

<section>
	<div class="form-wrap">
		<div class="mb-6">
			<Label for="email" class="mb-2">Email</Label>
			<Input type="email" id="email" placeholder="voa@voa.com" required="" bind:value={loginInput.email} />
		</div>
		<div class="mb-6">
			<Label for="password" class="mb-2">Password</Label>
			<Input type="password" id="password" required="" bind:value={loginInput.password} />
		</div>
		<Checkbox class="mb-6">Remember me</Checkbox>
		<Button color="blue" type="submit" on:click={login}>Login</Button>
	</div>
</section>

<style>
    section {
        width: 100%;
    }

    .form-wrap {
        width: 45vh;
        margin: auto;
    }
</style>