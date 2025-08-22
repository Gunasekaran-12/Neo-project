const originalError = console.error;

beforeAll(() => {
  jest.spyOn(console, 'error').mockImplementation((...args) => {
    if (
      typeof args[0] === 'string' &&
      args[0].includes('ReactDOMTestUtils.act is deprecated')
    ) {
      return; // ignore this one warning
    }
    originalError(...args);
  });
});

afterAll(() => {
  console.error.mockRestore();
});
