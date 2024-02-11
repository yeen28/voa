<script>
	import { Label, Input, Checkbox, Button } from 'flowbite-svelte';

	function login() {
		const email = document.getElementById('email').value;
		const password = document.getElementById('password').value;

		if (email === '' || password === '') {
			alert('이메일 또는 비밀번호를 입력해 주세요.');
			return;
		}

		const data = JSON.stringify({'email': email, 'password': password});

		fetch('/login/user', {
			method: 'POST',
			headers: {'Content-Type': 'application/json'},
			body: data
		})
			.then(response => response.json())
			.then(response => {
				if (!!response.email) {
					window.location.href = '/main';
				}
			})
			.catch(e => console.log(e));
	}
</script>

<section>
	<div class="form-wrap">
		<div class="mb-6">
			<Label for="email" class="mb-2">Email</Label>
			<Input type="email" id="email" placeholder="voa@voa.com" required="" value="admin@email.com" />
		</div>
		<div class="mb-6">
			<Label for="password" class="mb-2">Password</Label>
			<Input type="password" id="password" required="" value="123" />
		</div>
		<Checkbox class="mb-6">Remember me</Checkbox>
		<Button color="blue" on:click={login}>Login</Button>
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